
const highlightColors = [
                        'highlight-color-player1',
                        'highlight-color-player2',
                        'highlight-color-player3',
                        'highlight-color-player4'
                    ];
    // highlight color depends on player
let currentPlayerIndex = undefined;

const startGameBtn = document.querySelector('.btnStart');
const create = document.querySelector('.btn');
const join = document.querySelector('.btn1');
const startGameBtn2 = document.querySelector('.btnStart2');
const lobby = document.querySelector('.lobby');
const header = document.querySelector('header');
const line = document.querySelector('.horizontal-line');
const body = document.querySelector('body');
const table = document.querySelector('table');
const header2 = document.getElementById('game-title');
const chatBox = document.querySelector('.chat');
const wordGrid = document.getElementById('wordGrid');
const wordBank = document.getElementById('wordBank');
const wordGrid2 = document.getElementById('wordGrid');
const wordBank2 = document.getElementById('wordBank');
const container = document.getElementById('container');
const lobby_players = document.querySelector('.lobby-players');
const player1 = document.querySelector('.player1'); 
const player2 = document.querySelector('.player2');
const player3 = document.querySelector('.player3');
const player4 = document.querySelector('.player4');

var userName = document.getElementById("username");         //send user name everytime to backend (add userName to array and if first then they get red and so on)
var userSubmit = document.getElementById("userSubmit");
var userText = document.getElementById("userText");
const getUserWindow = document.getElementById("getUsernameWindow");
var game;

userSubmit.addEventListener("click", function()
{
    var data = {
        "username":userName.value
    }
    //userText.innerHTML = JSON.stringify(data);
    if(userName.value != null) {
        connection.send("username: " + userName.value);
        // Username is checked and allows user to continue if username is unique and valid
    }
})

//game2
//first button goes to wordgrid2
//second button goes to wordgri
startGameBtn.addEventListener('click', () => {

    // Fetch word grid data from server
fetch('/wordgrid')
.then(response => response.text())
.then(htmlGrid => {
    //console.log(response.text())
    //console.log(response)
    game = 2
    if (currentPlayerIndex === undefined) {
        currentPlayerIndex = 1
    } else if(currentPlayerIndex === 1) {
        currentPlayerIndex = 2;
    }
    console.log("PLAYER:" +currentPlayerIndex);

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
            const currentPlayerClass = `highlight-color-player${currentPlayerIndex}`; // Determine current player's highlight class
            if (!cell.classList.contains(currentPlayerClass)) {
                cell.classList.add(currentPlayerClass); // Add highlight class for current player
            } else {
                cell.classList.remove(currentPlayerClass); // Remove highlight class for current player
            }
        };
    });

    //maybe call backend to see which color
})
.catch(error => console.error('Error fetching word grid:', error));

  // gets the wordbank sent from server
    fetch('/wordbank')
    .then(response => response.text())
    .then(htmlWordBank => {
        const wordBankDiv = document.getElementById('wordBank');
        wordBankDiv.innerHTML = htmlWordBank;
    })
    .catch(error => console.error('Error fetching word bank:', error));
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

    line.style.top = '50px';
    line.style.width = '65%';
    
});

//game1
startGameBtn2.addEventListener('click', () => {

    // Fetch word grid data from server
fetch('/wordgrid2')
.then(response => response.text())
.then(htmlGrid2 => {
    //console.log(response.text())
    //console.log(response)
    game = 1;

    if (currentPlayerIndex === undefined) {
        currentPlayerIndex = 1
    } else if(currentPlayerIndex === 1) {
        currentPlayerIndex = 2;
    }
    console.log("PLAYER:" +currentPlayerIndex);

    let obj = {
        type: "a"
    };
    connection.send(JSON.stringify(obj));

    console.log(htmlGrid2)
    console.log("testestset")
    // Convert the HTML string to DOM elements
    const tempDiv2 = document.createElement('div');
    tempDiv2.innerHTML = htmlGrid2.trim();

    // Create a new table element
    const newTable2 = document.createElement('table');
    newTable2.style.borderCollapse = 'collapse'; // Ensure borders collapse properly

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
            const gridCell = tempDiv2.querySelector(`tr:nth-child(${i + 1}) td:nth-child(${j + 1})`);
            if (gridCell) {
                newCell.textContent = gridCell.textContent;
            }
            newRow.appendChild(newCell); // Append the new cell to the new row
        }
        newTable2.appendChild(newRow); // Append the new row to the new table
    }
    console.log(newTable2)
    // Insert the new table into the wordGrid element
    document.getElementById('wordGrid').appendChild(newTable2);

    // Add click event listener to table cells for highlighting
    newTable2.querySelectorAll('td').forEach(cell => {
        cell.onclick = function() {
            const currentPlayerClass = `highlight-color-player${currentPlayerIndex}`; // Determine current player's highlight class
            if (!cell.classList.contains(currentPlayerClass)) {
                cell.classList.add(currentPlayerClass); // Add highlight class for current player
            } else {
                cell.classList.remove(currentPlayerClass); // Remove highlight class for current player
            }
        };
    });
})
.catch(error => console.error('Error fetching word grid:', error));

  // gets the wordbank sent from server
    fetch('/wordbank2')
    .then(response => response.text())
    .then(htmlWordBank2 => {
        const wordBankDiv = document.getElementById('wordBank');
        wordBankDiv.innerHTML = htmlWordBank2;
    })
    .catch(error => console.error('Error fetching word bank:', error));
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

    line.style.top = '50px';
    line.style.width = '65%';
    
});
create.addEventListener('click', () => {
    /* if(player1.classList.contains('hidden')) {
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
        
    } */
    connection.send("Create new room");
});
join.addEventListener('click', () => {
    /* if (!player1.classList.contains('hidden')) {
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
    } */
    connection.send("Join room");
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
            console.log(userName.value);
            let obj = {
                type: "letterSelection",
                firstLetterCoordinate: [firstLetter.dataset.x, firstLetter.dataset.y],
                secondLetterCoordinate: [event.target.dataset.x, event.target.dataset.y],
                game: game,
                userName: userName.value
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

function highlightWord(firstLetter, secondLetter, userId) {

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
        const currentPlayerClass = `highlight-color-player${currentPlayerIndex}`; 
        if (!cellItem.classList.contains(currentPlayerClass)) {
            cellItem.classList.add(currentPlayerClass); 
        }
                //change to corr player color
        if (userId === 1){
            cellItem.style.backgroundColor = `rgba(204, 50, 50, 0.631)`;
        }
        else if (userId === 2){
            cellItem.style.backgroundColor = `rgba(40, 40, 165, 0.693)`;
        }
        else if (userId === 3){
            cellItem.style.backgroundColor = `rgb(0, 255, 0.001)`;
        }
        else if (userId === 4){
            cellItem.style.backgroundColor = `rgb(255, 255, 0.001)`;
        }
        //cellItem.style.pointerEvents = 'none';
        x += changeX;
        y += changeY;
    }
}

connection.onmessage = function (evt) {
    var msg = evt.data;
    msg = JSON.parse(evt.data);
    console.log("Message Received: ", msg);

    // Once input from user is given, lobby will appear if username is valid
    if(msg.msg == "Username taken") {
        userText.innerHTML = "Username taken, please try again";
    }
    else if(msg.msg == "Username valid") {
        userName.classList.add('hidden');
        userSubmit.classList.add('hidden');
        userText.classList.add('hidden');
        getUserWindow.classList.add('hidden');

        lobby.classList.remove('hidden');
    }

    // Updates the leaderboard whenever a new player joins
    if(msg.type == "leaderboard") {
        try {
            var leaderboard = JSON.parse(msg.msg);
            console.log(leaderboard);
            displayLeaderboard(leaderboard);
        }
        catch (error) {
            console.error("Error parsing leaderboard JSON: " + error);
        }
    }
    
    // Updates the lobby player list whenever a new player joins
    if(msg.type == "playerlist") {
        try {
            var playerlist = JSON.parse(msg.msg);
            console.log(playerlist);
            displayLobby(playerlist);
        }
        catch (error) {
            console.error("Error parsing playerlist JSON: " + error);
        }
    }
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
        var parsedGame = JSON.parse(msg.game);
        if (parsedGame == game) {
            var userId = JSON.parse(msg.userId)
            console.log("USER ID: " + userId)
            highlightWord(JSON.parse(msg.firstLetter), JSON.parse(msg.secondLetter), userId)
        }
        
    }

    if ("version" in msg){
      document.title = "Version " + msg.version;
    }
    
}

function displayLeaderboard(leaderboard) {
    var leaderboardElement = document.getElementById("leaderboard");
    leaderboardElement.innerHTML = ""; // Removes all elements of the list

    leaderboard.players.forEach(function(player) {
        var listItem = document.createElement("li");
        listItem.textContent = player.playerName + " | " + player.score; // The new list item contains the name and corresponding score
        leaderboardElement.appendChild(listItem); // Adds the name and score to the list, which displays in the lobby
    });
}

function displayLobby(playerlist) {
    var lobbyElement = document.getElementById("lobbyPlayers");
    lobbyElement.innerHTML = "";

    playerlist.forEach(function(player) {
        var listItem = document.createElement("li");
        listItem.textContent = player.playerName;
        lobbyElement.appendChild(listItem);
    });
}

function toggleInstructions() {
    var instructions = document.getElementById("instructions");
    if (instructions.style.display === "none") {
        instructions.style.display = "block";
      } else {
        instructions.style.display = "none";
      }
}

    


