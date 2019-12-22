const GAMEBOARD_ID = "gameBoard";
const CONTROLS_ID = "controls";

const BLANK_SQUARE_CLASS = "square blank";
const SQUARE_CLASS = "square";

// used for labeling different sections of the board
const UNCOLOURED_COLOUR = '#bcc0c4';
const COLOURS = [ UNCOLOURED_COLOUR, '#1260de', '#e06767', '#734e4e', '#825d35', '#35cc58', '#7350de', '#eb26d0', '#e31010'];
const NUM_COLUMNS_COLOURS = 5;

const DEFAULT_NUM_ROWS = 5;
const DEFAULT_NUM_COLUMNS = 5;
const DEFAULT_NUM_MINES = 3;

let numberOfRows = DEFAULT_NUM_ROWS;
let numberOfColumns = DEFAULT_NUM_COLUMNS;
let colourSelected;
let lastSelectedElement;

window.onload = function() {
  // remove context menu, so we can right click
  document.oncontextmenu = function(e) {return false; };

  // add input event listeners for width / height
  document.getElementById('addWidth').addEventListener('click', function(){addWidth(1)});
  document.getElementById('addHeight').addEventListener('click', function(){addHeight(1)});
  document.getElementById('subtractWidth').addEventListener('click', function(){addWidth(-1)});
  document.getElementById('subtractHeight').addEventListener('click', function(){addHeight(-1)});
  
  // Create default board
  createBoardToUI(numberOfColumns, numberOfRows);
  document.getElementById('minesInput').value = DEFAULT_NUM_MINES;
  
  // create pallete of colours
  createControls();
  
  //
  createKeyEventListener();
  
  document.getElementById('saveBoard').addEventListener('click', saveBoard);
}

function saveBoard() {
  let jsonData = {
    mines:  document.getElementById('minesInput').value,
    width: numberOfRows,
    height: numberOfColumns
  };
  
  for (let x=0; x<numberOfRows; x++) {
    for (let y=0; y<numberOfColumns; y++) {
      //console.log(document.getElementById(x + "_" + y));
    }
  }
  console.log(jsonData);
}

function createKeyEventListener() {
  document.addEventListener('keyup', (e) => {
    if (lastSelectedElement) {
      if (e.key == "Backspace" || e.key == "Delete") {
        lastSelectedElement.innerHTML = "|";
      } else if (e.key.length == 1) {
        lastSelectedElement.innerHTML = String(e.key).toUpperCase();
        lastSelectedElement = null;
      }
    }
  });
}

function createControls() {
  let htmlString = "<table id='colours'><thead></thead><tbody><tr>";

  for (let i=0; i<COLOURS.length; i++) {
    htmlString += createTD(COLOURS[i]);
    if (i > 0 && i % (NUM_COLUMNS_COLOURS - 1) === 0) {
      htmlString += "</tr><tr>";
    }
  }
  
  htmlString += "</tr></tbody></table>";
  
  document.getElementById(CONTROLS_ID).innerHTML = htmlString;
}

function createTD(colour) {
  return "<td style='background-color: " + colour + "' onclick='setColour(\"" + colour + "\")'></td>";
}

function setColour(colour) {
  colourSelected = colour;
}

function createBoardToUI(numberOfColumns, numberOfRows) {
  let existingItems = getIdsWithChanges();
  
  document.getElementById(GAMEBOARD_ID).innerHTML = "";
  for (let i=0; i<numberOfRows; i++) {
    document.getElementById(GAMEBOARD_ID).innerHTML += createRow(i, numberOfColumns);
  }
  
  document.getElementById('widthInput').value = numberOfColumns;
  document.getElementById('heightInput').value = numberOfRows;
  
  // re-create old items
  for (let i=0; i<existingItems.length; i++) {
    let elementToModify = document.getElementById(existingItems[i].itemId);
    
    if (elementToModify) {
      console.log(existingItems[i]);
      elementToModify.style.backgroundColor = existingItems[i].colour;
      elementToModify.innerHTML = existingItems[i].innerHTML;
    }
  }
}

function getIdsWithChanges() {
  let results = [];
  let items = document.getElementsByClassName(SQUARE_CLASS);
  
  for (let i=0; i<items.length; i++) {
    if (items[i].style.backgroundColor) {
      let hexValue = rgb2hex(items[i].style.backgroundColor);
      if (hexValue != UNCOLOURED_COLOUR) {
        results.push({itemId: items[i].id, colour: hexValue, innerHTML: items[i].innerHTML});
      } else if (items[i].innerHTML) {
        results.push({itemId: items[i].id, colour: UNCOLOURED_COLOUR, innerHTML: items[i].innerHTML});
      }
    } else if (items[i].innerHTML) {
      results.push({itemId: items[i].id, colour: UNCOLOURED_COLOUR, innerHTML: items[i].innerHTML});
    }
  }
  
  return results;
}

function rgb2hex(rgb) {
    rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
    function hex(x) {
        return ("0" + parseInt(x).toString(16)).slice(-2);
    }
    return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
}

function addWidth(amount) {
  numberOfColumns += amount;
  createBoardToUI(numberOfColumns, numberOfRows);
}

function addHeight(amount) {
  numberOfRows += amount;
  createBoardToUI(numberOfColumns, numberOfRows);
}

function addColour(elementClicked) {
  if (colourSelected) {
    elementClicked.style.backgroundColor = colourSelected;
  }
}

// + " ";
function createColumn(rowNumber, columnNumber) {
  return "<div class='" + BLANK_SQUARE_CLASS + "' id='" + (rowNumber + "_" + columnNumber) + "' onclick='addColour(this)' "
  + "oncontextmenu='typeIntoElement(this)'"
  + "></div>";
}

function typeIntoElement(elementToModify) {
  if (lastSelectedElement) {
    lastSelectedElement.innerHTML = "";
  }
  elementToModify.innerHTML += "|";
  lastSelectedElement = elementToModify;
}

function createRow(rowNumber, numberOfColumns) {
  let html = "<div id='row_" + rowNumber + "'>";
  
  for(let i=0; i<numberOfColumns; i++) {
    html += createColumn(rowNumber, i);
  }
  
  return html + "<div class='nextRow'></div>";
}
