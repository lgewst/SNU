var java = require('java');

java.classpath.push('./');
var Test = java.import('test_nw/Test');

var result = Test.addNumbersSync(2, 3);
console.log(result);
