

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'highlight'
})

export class HighlightSearch implements PipeTransform {

  transform(text: any, keyword: any): any {
      if (!keyword || !text) {return text;}
      // var re = new RegExp(keyword, 'ugi'); //'gi' for case insensitive and can use 'g' if you want the search to be case sensitive.
      // return text.replace(re, "<mark><b>$&</b></mark>");
      let openTag = "<b>";
      let closeTag = "</b>";

      for(let i = 0; i<text.length; i++){
        let slice = text.substr(i,keyword.length);

        if(slice.localeCompare(keyword,'en', {sensitivity: 'base'}) == 0){          
          let firstPart = text.slice(0,i);
          let secondPart = text.slice(i);
          text = firstPart + secondPart.replace(slice, openTag + slice + closeTag);
          i = i + openTag.length + keyword.length + closeTag.length - 1;
        }
      }

      return text;
  }

}