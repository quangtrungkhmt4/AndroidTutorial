const http = require('http');

let app = http.createServer((request, response) => {
	response.writeHead(200, {'Content-Type': 'text/plain'})
	response.end('Hello World!\n');
});

app.listen(3000, 'localhost');
console.log('Node server is running on port 3000');