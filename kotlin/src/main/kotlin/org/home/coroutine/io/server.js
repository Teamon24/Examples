const {createServer} = require("http");

let reqCount = 0;
const server = createServer(
    async (req, res) => {
        const {method, url} = req;
        const reqNumber = ++reqCount;
        console.log(`${new Date().toISOString()} [${reqNumber}] ${method} ${url}`);

        await new Promise((resolve) => setTimeout(resolve, 5000));
        res.end("Hello!\n");

        console.log(`${new Date().toISOString()} [${reqNumber}] done!`);
    });

server.listen(8080);
console.log("Server started!");