/*
  example.js - custom script for bitbucket-webhook-example
  Copyright (C) 2015 Nishimura Software Studio

  Copying and distribution of this file, with or without modification, are
  permitted in any medium without royalty provided the copyright notice and
  this notice are preserved.  This file is offered as-is, without any warranty.
*/

"use strict";

$(document).ready(function () {
    var accountMenu = $("#account-menu");
    accountMenu.removeClass("pure-menu-allow-hover");
    accountMenu.mouseleave(function (event) {
        $("#account-menu > .pure-menu-children").css("display", "");
    });

    $("#account-menu > .pure-menu-link").click(function (event) {
        var children = $("#account-menu > .pure-menu-children");
        if (children.css("display") !== "block") {
            children.css("display", "block");
        } else {
            accountMenu.mouseleave();
        }
        return false;
    });
});
