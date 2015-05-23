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
