(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
'use strict'

var Status = {
  url: 'http://huevos.com:8081/timone/services/statuses',
  list: function(params) {
    var _self = this;
    return new RSVP.Promise(function(resolve, reject) {
      $.getJSON(_self.url, params.query).done(function(response) {
        resolve(response);
      }).fail(function(response) {
        reject(response);
      });
    });
  },
};

module.exports = Status

},{}],2:[function(require,module,exports){
var Status = require('../app/status/project_status.js');

describe("Statuses of a project", function() {
  var statuses = []

  beforeEach(function(done) {
    Status.list({}).then(function(response) {
      console.log("response");
      console.log(response);
      statuses = response;
    }, function(error) {
      console.log("error");
      console.log(error);
    }).finally(function() { done() });
  });

  it("should get all statuses of a project", function(done) {
    expect(statuses).not.toEqual([])
    done();
  });
});

},{"../app/status/project_status.js":1}]},{},[2]);
