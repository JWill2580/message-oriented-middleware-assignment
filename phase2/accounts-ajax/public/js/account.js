/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

"use strict";

var module = angular.module('AccountApp', ['ngResource']);
let serviceURI = 'http://localhost:9000/api';

module.factory('accountApi', function($resource){
    return $resource(serviceURI);
});

module.controller('AccountController', function (accountApi) {
    this.test = "It works!";
    this.addAccount = function (accountToAdd) {
        accountApi.save({}, accountToAdd);
    };
});
