
const highlightColors = [
                        'highlight-color-player1',
                        'highlight-color-player2',
                        'highlight-color-player3',
                        'highlight-color-player4'
                    ];
    // highlight color depends on player
    let currentPlayerIndex = 1;

const startGameBtn = document.querySelector('.btnStart');
const join = document.querySelector('.btn');
const leave = document.querySelector('.btn1');
const lobby = document.querySelector('.lobby');
const header = document.querySelector('header');
const body = document.querySelector('body');
const table = document.querySelector('table');
const header2 = document.getElementById('game-title');
const chatBox = document.querySelector('.chat');
const wordGrid = document.getElementById('wordGrid');
const wordBank = document.getElementById('wordBank');
const container = document.getElementById('container');
const lobby_players = document.querySelector('.lobby-players');
const border = document.querySelector('.border');
const player1 = document.querySelector('.player1'); 
const player2 = document.querySelector('.player2');
const player3 = document.querySelector('.player3');
const player4 = document.querySelector('.player4');

const line = document.querySelector('.horizontal-line');

var username = document.getElementById("username");
var userSubmit = document.getElementById("userSubmit");
var userText = document.getElementById("userText");

userSubmit.addEventListener("click", function() {
    var data = {
        "username":username.value
    }
    //userText.innerHTML = JSON.stringify(data);
    if(username.value != null) {
        connection.send("username: " + username.value);
    }
})
    
startGameBtn.addEventListener('click', () => {

    // Fetch word grid data from server
fetch('/wordgrid')
.then(response => response.text())
.then(htmlGrid => {
    //console.log(response.text())
    //console.log(response)

    let obj = {
        type: "a"
    };
    connection.send(JSON.stringify(obj));

    console.log(htmlGrid)
    console.log("testestset")
    // Convert the HTML string to DOM elements
    const tempDiv = document.createElement('div');
    tempDiv.innerHTML = htmlGrid.trim();

    // Create a new table element
    const newTable = document.createElement('table');
    newTable.style.borderCollapse = 'collapse'; // Ensure borders collapse properly

    // Create 20 rows and 20 columns in the table
    for (let i = 0; i < 20; i++) {
        const newRow = document.createElement('tr');
        for (let j = 0; j < 20; j++) {
            const newCell = document.createElement('td');
            newCell.style.border = '1px solid black'; // Add border around each cell
            newCell.style.textAlign = 'center'; // Center text horizontally
            newCell.style.verticalAlign = 'middle'; // Center text vertically
            newCell.style.width = '20px'; // Set width to 20px
            newCell.style.height = '15px'; // Set height to 15px
            newCell.style.cursor = 'pointer'; // Add cursor pointer
            newCell.dataset.x = i;
            newCell.dataset.y = j;
            newCell.id = `cell-${i}-${j}`;
            // Copy the text content of the corresponding cell in the grid
            const gridCell = tempDiv.querySelector(`tr:nth-child(${i + 1}) td:nth-child(${j + 1})`);
            if (gridCell) {
                newCell.textContent = gridCell.textContent;
            }
            newRow.appendChild(newCell); // Append the new cell to the new row
        }
        newTable.appendChild(newRow); // Append the new row to the new table
    }
    console.log(newTable)
    // Insert the new table into the wordGrid element
    document.getElementById('wordGrid').appendChild(newTable);

    // Add click event listener to table cells for highlighting
    newTable.querySelectorAll('td').forEach(cell => {
        cell.onclick = function() {
            const currentPlayerClass = `highlight-color-player${currentPlayerIndex + 1}`; // Determine current player's highlight class
            if (!cell.classList.contains(currentPlayerClass)) {
                cell.classList.add(currentPlayerClass); // Add highlight class for current player
            } else {
                cell.classList.remove(currentPlayerClass); // Remove highlight class for current player
            }
        };
    });
})
.catch(error => console.error('Error fetching word grid:', error));

    lobby.classList.add('hidden');
    header.classList.add('hidden');
    body.classList.add('remove-background');
    header2.classList.remove('hidden');
    chatBox.classList.remove('hidden');
    wordGrid.classList.remove('hidden');
    wordBank.classList.remove('hidden');
    lobby.classList.add('scale');
    container.classList.add('leader');
    lobby_players.classList.add('hidden');
    border.classList.add('hidden');
    line.style.top = '80px';
    line.style.width = '50%';

    
});
join.addEventListener('click', () => {
    if(player1.classList.contains('hidden')) {
        player1.classList.remove('hidden');
        
    }
    else if (player2.classList.contains('hidden')) {
        player2.classList.remove('hidden');
        
    }
    else if (player3.classList.contains('hidden')) {
        player3.classList.remove('hidden');
        
    }
    else if (player4.classList.contains('hidden')) {
        player4.classList.remove('hidden');
        
    }
});
leave.addEventListener('click', () => {
    if (!player1.classList.contains('hidden')) {
        player1.classList.remove('hidden');
        
        }
    else if (!player2.classList.contains('hidden')) {
        player2.classList.remove('hidden');

    }
    else if (!player3.classList.contains('hidden')) {
        player3.classList.remove('hidden');
    }
    else if (!player4.classList.contains('hidden')) {
        player4.classList.remove('hidden');
    }
    
});




let firstLetter = null;
let secondLetter = null;
wordGrid.addEventListener('click', function (event) {

        

        if (!firstLetter) {
            console.log("a")
            console.log(wordGrid)

            console.log(event.target)
            console.log(event.target.dataset.x)
            console.log(event.target.dataset.y)

            firstLetter = event.target;


        } else if (!secondLetter && event.target !== firstLetter) {
            secondLetter = event.target;
            console.log(event.target.dataset.x)
            console.log(event.target.dataset.y)

            let obj = {
                type: "letterSelection",
                firstLetterCoordinate: [firstLetter.dataset.x, firstLetter.dataset.y],
                secondLetterCoordinate: [event.target.dataset.x, event.target.dataset.y]
            };
            connection.send(JSON.stringify(obj));

            if (firstLetter !== null && secondLetter !== null) {
                firstLetter = null;
                secondLetter = null;
            }
        }
    
});

        
// highlights letters
for (let node of document.querySelectorAll("td")){
    node.onclick = function() {
        if(node.className == ""){
            node.classList.add(highlightColors[currentPlayerIndex]);
        } else {
            node.className = ""
        }
    }
}
var connection = null;

var serverUrl;
serverUrl = "ws://" + window.location.hostname + ":9104";
// Create the connection with the server
connection = new WebSocket(serverUrl);

connection.onopen = function (evt) {
    console.log("open");
    //connection.send(JSON.stringify({ type: "getUsername" }));
}
//if close then set topMessage to = Server Offline
connection.onclose = function (evt) {
    console.log("close");
    document.getElementById("topMessage").innerHTML = "Server Offline"
}

/* connection.onmessage (evt) {
    console.log("Message received from server: " + evt.data);
    var message = JSON.parse(evt.data);
    if(message.type == "promptUsername") {
        var username = prompt(message.text);
        if(username != null) {
            connection.send(JSON.stringify({ type: "username", value: username}));
        } 
    }
    else {
        document.getElementById("messages").innerText += message.text + "\n";
    }
} */

document.addEventListener('DOMContentLoaded', function() {
    const chatBox = document.getElementById('chatBox');
    const userInput = document.getElementById('userInput');
    const send = document.getElementById('send');

    send.addEventListener('click', function() {
        const message = userInput.value.trim();
        addMessage(message);
        userInput.value = '';
    });

    function addMessage(message) {
        connection.send("msg: "+ message);
        /*
        const msgElement = document.createElement('div');
        msgElement.textContent = message;       //text content of the new div
        chatBox.appendChild(msgElement);        //appending the new message to message history
        chatBox.scrollTop = chatBox.scrollHeight;
        */

        //call function in messaging for other player to see message
    }
});

function highlightWord(firstLetter, secondLetter, playerColor) {

    const X1 = firstLetter[0];
    const Y1 = firstLetter[1];
    const X2 = secondLetter[0];
    const Y2 = secondLetter[1];

    const changeX = X1 === X2 ? 0 : (X2 > X1 ? 1 : -1);
    const changeY = Y1 === Y2 ? 0 : (Y2 > Y1 ? 1 : -1);

    let x = X1;
    let y = Y1;

    while (x !== X2 + changeX || y !== Y2 + changeY) {
        const cellItem = document.getElementById(`cell-${x}-${y}`);
        const currentPlayerClass = `highlight-color-player${currentPlayerIndex + 1}`; 
        if (!cellItem.classList.contains(currentPlayerClass)) {
            cellItem.classList.add(currentPlayerClass); 
        }
        //cellItem.style.backgroundColor = `blue`        //change to corr player color
        cellItem.style.pointerEvents = 'none';
        x += changeX;
        y += changeY;
    }
}


connection.onmessage = function (evt) {
    

    msg = JSON.parse(evt.data);
    console.log("Message Received: ", msg);

    //console.log("Message received: " + msg);

    if (msg.type == "message") {
        // If yes, remove the prefix
        msg = msg.msg.substring(4, msg.msg.length);
        const msgElement = document.createElement('div');
        msgElement.textContent = msg;       //text content of the new div
        chatBox.appendChild(msgElement);        //appending the new message to message history
        chatBox.scrollTop = chatBox.scrollHeight;
    } 
    else if (msg.type == "valid") {
        
        highlightWord(JSON.parse(msg.firstLetter), JSON.parse(msg.secondLetter))
    }


    
}

function toggleInstructions() {
    var instructions = document.getElementById("instructions");
    if (instructions.style.display === "none") {
        instructions.style.display = "block";
      } else {
        instructions.style.display = "none";
      }
}

    // gets the wordbank sent from server
    fetch('/wordbank')
    .then(response => response.text())
    .then(htmlWordBank => {
        const wordBankDiv = document.getElementById('wordBank');
        wordBankDiv.innerHTML = htmlWordBank;
    })
    .catch(error => console.error('Error fetching word bank:', error));


