/******************************************************
 * Copyright 2007, LoopFuse, LLC, All Rights Reserved *
 *                                                    *
 *                                                    *
 *     ~Lasciate ogne speranza, voi ch'intrate~       *
 *                                                    *
 /****************************************************/

/* customer id */
var _lf_cid = "";

/* this */
var _lf_doc = document;

/* <title>.*</title> */
var _lf_doc_title = _lf_doc.title;

/* Where we record hits */
var _lf_loopfusePageProtocol = window.location.protocol + '//';
var _lf_loopfuseHomeURL = _lf_loopfusePageProtocol + "loopfuse.net";
var _lf_recorder_path = "/webrecorder/record";
var _lf_cHost;

/* Server-generated GUID path */
var _lf_loopfuseVIDURL = "loopfuse.net/webrecorder/carbine/caine.js";

/* GUID */
var _lf_vid;

var _lf_kid = _lf_getURLParam("x_lf_kid");
var _lf_kt = _lf_getURLParam("x_lf_kt");
var _lf_kr = _lf_getURLParam("_x_lf_kr");

/* Cookie Name **/
var _lf_cName = "LOOPFUSE";

/* http://en.wikipedia.org/wiki/Remora */
function _lf_remora()
{
   if (_lf_cid == undefined || _lf_cid == '')
      return;

   if (_lf_cHost != undefined && _lf_cHost != '')
      _lf_loopfuseHomeURL = "http://" + _lf_cHost + _lf_recorder_path;
   else
      _lf_loopfuseHomeURL += _lf_recorder_path;

   var _lf_ref = "&from=" + _lf_getReferer();
   var _lf_resolution = "&res=" + window.screen.width + 'x' + window.screen.height;
   var _lf_title = "&t=" + escape(_lf_doc_title);
   var _vid = "&vid=" + _lf_vid;
   var _campy;
   var _lf_trackURL = "";

   _lf_trackURL += _lf_loopfuseHomeURL + "?cid=" + _lf_cid + _vid + _lf_ref + _lf_title + _lf_resolution;

   if (_lf_kid != "" && _lf_kid != undefined && _lf_kt != undefined && _lf_kt != "")
      _campy = "&kid=" + _lf_kid + "&kt=" + _lf_kt;
   if (_lf_kr != "" && _lf_kr != undefined)
      _campy += "&kr=" + _lf_kr;
   if (_campy != "" && _campy != undefined)
      _lf_trackURL += _campy;

   _lf_trackURL += '&' + Math.random()
   _lf_setCookie();

   if (navigator.appName == 'Netscape')  // Netscape hack
   {
      document.write('<la' + 'yer hidden=true><im' + 'g src="' + _lf_trackURL + '" border=0 width=1 height=1 ><\/la' + 'yer>');
   }
   else
   {
      document.write('<im' + 'g style="display:none" src="' + _lf_trackURL + '" border=0 width=5 height=5>');
   }

   _lf_modForms();
}

function _lf_remora_tracker(_page, _red)
{
   if (_lf_cid == undefined || _lf_cid == '')
      return;

   if (_page == undefined || _page == '')
      return;

   if (_lf_cHost != undefined && _lf_cHost != '')
      _lf_loopfuseHomeURL = "http://" + _lf_cHost + _lf_recorder_path;
   else
      _lf_loopfuseHomeURL += _lf_recorder_path;

   var _lf_this = "&from=" + _lf_doc.URL;
   var _lf_resolution = "&res=" + window.screen.width + 'x' + window.screen.height;
   var _lf_title = "&t=" + escape(_lf_doc_title);
   var _vid = "&vid=" + _lf_vid;
   var _lf_to = "&to=" + _page;
   var _lf_trackURL = "";

   _lf_trackURL += _lf_loopfuseHomeURL + "?cid=" + _lf_cid + _vid + _lf_this + _lf_title + _lf_resolution + _lf_to;
   _lf_trackURL += '&' + Math.random()

   if (navigator.appName == 'Netscape')  // Netscape hack
   {
      document.write('<la' + 'yer hidden=true><im' + 'g src="' + _lf_trackURL + '" border=0 width=1 height=1 ><\/la' + 'yer>');
   }
   else
   {
      document.write('<im' + 'g style="display:none" src="' + _lf_trackURL + '" border=0 width=5 height=5>');
   }

   if (_red == undefined || _red == '')
   {
      window.location = _page;
   }
}

function _lf_getURLParam(name)
{
   var value = "";
   var href = window.location.href;
   if (href.indexOf("?") > -1)
   {
      var queryString = href.substr(href.indexOf("?")).toLowerCase();
      var queryParams = queryString.split("&");
      for (var i = 0; i < queryParams.length; i++)
      {
         if (queryParams[i].indexOf(name + "=") > -1)
         {
            var aParam = queryParams[i].split("=");
            value = aParam[1];
            break;
         }
      }
   }
   return value;
}

function _lf_getGUID()
{
   _lf_vid = _lf_getCookie(_lf_cName);
   if (_lf_vid == undefined || _lf_vid == "" || _lf_vid == null || _lf_vid == "null")
   {
      if (_lf_kt == '2')
      {
         _lf_vid = _lf_getURLParam("_x_lf_kvid");
      }
      else
      {
         document.write("<SCR" + "IPT TYPE='text/javascript' SRC='" + "http" + (
            window.location.protocol.indexOf('https:') == 0 ? 's' :
            '') + "://" + _lf_loopfuseVIDURL + "'><\/SCR" + "IPT>");
      }
   }
}

function _lf_setCookie()
{
   var date = new Date(2020, 1, 1);
   var expires = ";expires=" + date.toGMTString();
   var _lf_domain = _lf_getDomain();
   var path = "/";

   document.cookie = _lf_cName + "=" + escape(_lf_vid) +
                     ( ( expires ) ? ";expires=" + expires : "" ) +
                     ( ( path ) ? ";path=" + path : "" ) +
                     ( ( _lf_domain ) ? ";domain=" + _lf_domain : "" );
}

function _lf_getCookie(name)
{
   var dc = document.cookie;
   var prefix = name + "=";
   var begin = dc.indexOf("; " + prefix);
   if (begin == -1)
   {
      begin = dc.indexOf(prefix);
      if (begin != 0) return null;
   }
   else
      begin += 2;
   var end = document.cookie.indexOf(";", begin);
   if (end == -1)
      end = dc.length;
   return unescape(dc.substring(begin + prefix.length, end));
}

function _lf_getDomain()
{
   var _lf_domain;
   if (_lf_doc.domain.substring(0, 4) == "www.")
   {
      _lf_domain = _lf_doc.domain.substring(4, _lf_doc.domain.length);
   }
   return _lf_domain;
}

function _lf_getReferer()
{
   var _lf_ref = escape(_lf_doc.referrer);
   if (_lf_ref == undefined || _lf_ref == '')
   {
      return '';
   }
   return _lf_ref;
}

function _lf_modForms()
{
   var _forms = _lf_doc.getElementsByTagName('form');
   for (var i = 0; i < _forms.length; i++)
   {
      var _objForm = _forms[i];
      var inputV = document.createElement('input');
      inputV.type = 'hidden';
      inputV.name = 'vid';
      inputV.value = _lf_vid;
      _objForm.appendChild(inputV);
      /*
      var inputC = document.createElement('input');
      inputC.type = 'hidden';
      inputC.name = 'cid';
      inputC.value = _lf_cid;
      _objForm.appendChild(inputC);*/
   }
}

_lf_getGUID();
