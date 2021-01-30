const fs = require('fs');
const file = "events.txt";

for(let a = 0 ;  a < 1 ; a++){ // every increase in "a" is around 220MB
    let events = 1000000;
    var start = []
    for(let a = 0 ; a < events ; a++) {
        start.push(Math.floor(Math.random()*100000000000000000))
    }
    var end = start;
    var ev = start
        .map(s => `{"id":"${s}", "state":"STARTED", "timestamp":1}`)
        .concat(
            end.map(s => `{"id":"${s}", "state":"FINISHED", "timestamp":${Math.floor(Math.random()*20)}}`))
            .sort( () => .5 - Math.random());

    ev.forEach(e => {
        fs.appendFileSync(file, `${e}\r\n`);
    })
};