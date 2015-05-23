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
