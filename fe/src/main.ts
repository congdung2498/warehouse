import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import {Constants} from "./app/shared/containts";

// transfers sessionStorage from one tab to another
const sessionStorage_transfer = (event) => {
  if (!event) { event = window.event; } // ie suq
  if (!event.newValue) { return; }        // do nothing if no value to work with
  if (event.key === 'getSessionStorage') {
    // another tab asked for the sessionStorage -> send it
    window.localStorage.setItem('sessionStorage', JSON.stringify(sessionStorage));
    // the other tab should now have it, so we're done with it.
    setTimeout(() => window.localStorage.removeItem('sessionStorage'), 500); // <- could do short timeout as well.
  } else if (event.key === 'sessionStorage' && !window.sessionStorage.length) {
    // another tab sent data <- get it
    const data = JSON.parse(event.newValue);
    for (const key in data) {
      if (key) {
        window.sessionStorage.setItem(key, data[key]);
      }
    }
  } else if (event.key === 'clearSessionStorage') {
    window.sessionStorage.clear();
    window.location.href = 'https://10.60.7.126:8663/passportv3/logout?appCode=PMQT&service=http://10.60.135.43:8080/PMQTVP';
  }
};

// listen for changes to window.localStorage
if (window.addEventListener) {
  window.addEventListener('storage', sessionStorage_transfer, false);
} else {
  window.addEventListener('onstorage', sessionStorage_transfer);
}


// Ask other tabs for session storage (this is ONLY to trigger event)
if (!window.sessionStorage.length) {
  window.localStorage.setItem('getSessionStorage', 'foobar');
  window.localStorage.removeItem('getSessionStorage');
}

if (environment.production) {
  enableProdMode();
}

// wait for return user information from server and replace the UserInfo in session storage
// wait for return user information from server and replace the UserInfo in session storage
setTimeout(() => {
  if (sessionStorage.getItem(Constants.TOKEN_KEY) != null) {
    fetch(Constants.HOME_URL + "/com/viettel/vtnet360/vt00/vt000000/16", {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': `bearer ${sessionStorage.getItem(Constants.TOKEN_KEY)}`
      },
      body: JSON.stringify({})
    }).then(function (response) {
      if (!response.ok) {
        window.localStorage.clear();
        window.sessionStorage.clear();
        window.location.href = 'https://10.60.7.126:8663/passportv3/logout?appCode=PMQT&service=http://10.60.135.43:8080/PMQTVP';

        throw Error(response.statusText);
      }
      // Read the response as json.
      return response.json();
    }).then(function (res) {
      //sessionStorage.setItem(Constants.USER_INFO, JSON.stringify(res.data));

      startApp();
    });
  } else {
    startApp();
  }


}, 2000);

function startApp() {
    // start app
    platformBrowserDynamic().bootstrapModule(AppModule)
      .catch(err => console.log(err))
}
