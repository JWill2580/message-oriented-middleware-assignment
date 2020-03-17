/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
"use strict";
let module = angular.module('AccountsModule', ['ngResource']);

module.controller('AccountsController', function (accountsApi, accountApi) {
// save 'this' so we can access it from other scopes
	let ctrl = this;
// get all products and load them into the 'products' model
	ctrl.accounts = accountsApi.query();
	
	this.addAccount = function (accountToAdd) {
		accountsApi.save({}, accountToAdd, function(){
			ctrl.accounts = accountsApi.query();
		});
	};
	
	this.deleteAccount = function (deletedId){
		accountApi.remove({id:deletedId}, function(){
			ctrl.accounts = accountsApi.query();
		});
	};
	this.updateAccount = function (updatedId){
		accountsApi.update({id:updatedId}, updatedId);
	};
});

let serviceURI = 'http://localhost:8080/api';

module.factory('accountsApi', function ($resource) {
	return $resource(serviceURI + '/accounts');
});

module.factory('accountApi', function ($resource) {
	return $resource(serviceURI + '/accounts/account/:id',
			  null, {update: {method: 'PUT'}});
});