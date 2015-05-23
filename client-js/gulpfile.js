var gulp = require('gulp'),
  connect = require('gulp-connect'),
  browserify = require('browserify'),
  transform = require('vinyl-transform'),
  concat = require('gulp-concat'),
  uglify = require('gulp-uglify'),
  jasmine = require('gulp-jasmine');

gulp.task('connect', function() {
  connect.server({
    root: './',
    port: 8091,
    fallback: 'tests/results.html',
    livereload: true
  });
});

gulp.task('html', function () {
  gulp.src('./tests/results.html')
    .pipe(connect.reload());
});

gulp.task('tests', function () {
  return gulp.src('./tests/*.js')
            .pipe(jasmine({
              verbose:true,
              includeStackTrace: true
            }));
});

gulp.task('browserify_test', function () {

  var browserified = transform(function(filename) {
    var b = browserify(filename);
    return b.bundle();
  });

  gulp.src('./tests/*_spec.js')
      .pipe(browserified)
      .pipe(concat('suite.js'))
      .pipe(gulp.dest('./tests'));
});

gulp.task('browserify', function () {

  var browserified = transform(function(filename) {
    var b = browserify(filename);
    return b.bundle();
  });

  gulp.src('./app/main.js')
      .pipe(browserified)
      //.pipe(uglify())
      // .pipe(concat('bundle.js'))
      .pipe(gulp.dest('./dist'));
});

gulp.task('watch', function () {
  gulp.watch(['./tests/results.html', './app/**/*.js', './tests/**/*.js'], ['html', 'browserify', 'browserify_test']);
});

gulp.task('default', ['connect', 'watch']);
