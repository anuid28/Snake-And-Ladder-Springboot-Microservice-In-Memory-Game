$( document ).ready(function() {
    $(".allImg").hide();

    $("#throwDice").click(function() {
        $.ajax({
            type: "GET",
            url: "/Game/nextMove",
            // data: data,
            beforeSend: function() {
             $("#throwDice").prop('disabled', true);
                $("#diceLoading").show();
                $(".allImg").hide();
                $(".allPlayer").removeClass('active');

            },
            success: function(result){
                let winner = result.winner;
                if(winner!=null){
                    $("#message").html(winner.name + ' won the game. Please start new game.').addClass('messageText');
                    $("#throwDice").hide();
                }
                let diceOutcome = result.diceOutcome;
                let message = result.message;
                let nextPlayerTurn = result.nextPlayerTurn;
                let currentPlayerTurn = result.currentPlayerTurn;
                let previousPositionOfCurrentPlayer =result.previousPositionOfCurrentPlayer;
                let updatedPlayerList = result.players.playerList;
                $("#liPlayer_"+currentPlayerTurn).addClass('active');
                $("#message").html(message).addClass('messageText');
                console.log("currentPlayerTurn= "+currentPlayerTurn);
                console.log("nextPlayerTurn= "+nextPlayerTurn);
                console.log("updatedPlayerList= "+JSON.stringify(updatedPlayerList));


                const snakeOrLadderPointer =result.snakeOrLadderPointer;


                $("#diceLoading").hide();
                $("#dice_"+diceOutcome).show();
                updatePlayerPosition(updatedPlayerList,currentPlayerTurn, previousPositionOfCurrentPlayer,snakeOrLadderPointer);


//                $("#td_"+previousPositionOfCurrentPlayer).addClass('yellowBox');
//                $("#td_"+previousPositionOfCurrentPlayer).fadeOut('slow');
//                $("#td_"+previousPositionOfCurrentPlayer).fadeIn('slow');
//
//                setTimeout(function() {
//                    $("#td_"+previousPositionOfCurrentPlayer).removeClass('yellowBox');
//                    },500
//                );
            },
            dataType: "json"
       }).done(function(result) {
     //  var diceOutcome = result.diceOutcome;
      // alert(diceOutcome);
     //  setTimeout(function() {

                      $("#throwDice").prop('disabled', false);
                    //  },1000
                   //);


       });;
});

    $("#newGame").click(function() {
    var elem = document.getElementById("body");
    enterFullScreen(elem);
        $(".allImg").hide();
        $("#message").html(" ").removeClass("messageText");
        $.ajax({
            type: "GET",
            url: "/Game/startNewGame",
            // data: data,
            success: function(result){
                const boardBox = result.gameBoard.board;
                const snakeLadder =result.snakeAndLadder.snakeAndLadderPointer
                const playerList =result.players.playerList;
                loadBoard(boardBox);
                loadSnakeAndLadder(snakeLadder);
                loadPlayers(playerList);
                $("#newGameMessage").hide();
                $("#playersTitle").show();
                $("#throwDice").show();

            },
            dataType: "json"
        });
    });


});

function getTimestampInSeconds () {
  return Math.floor(Date.now() / 1000)
}

function loadBoard(boardBox) {
    let len = boardBox.length;
    let  board = '<tr>';
    let rowCount =1;
    let j =9;
    for(let i= len; i>0; i-- ) {
        let tdClass ='normal';
        let tdText = i;
        if(i==100) {
            tdClass ='home';
            tdText = "Home";
        }
        if(i==10) {
            tdClass ='start';
            tdText = "Start";
        }
        if(i%10==0 && i<len) {
            board = board+'</tr><tr>';
            rowCount++;
            j =9;
        }
        if(rowCount%2==0) {
            let newValue =  i - j;
            tdText = newValue;
            if(i==10) {
                tdText = "Start";
             }
            board = board+'<td id="td_'+newValue+'" class="'+tdClass+'">'+tdText+'</td>';
             j=  j-2;
        }else {
         board = board+'<td id="td_'+i+'" class="'+tdClass+'">'+tdText+'</td>';
        }
    }
    board = board+'</tr>';
    $("#board").html(board);

}

function loadSnakeAndLadder(snakeLadder) {
    for (var i = 0, keys = Object.keys(snakeLadder), keyLen = keys.length; i < keyLen; i++) {
        let key = keys[i];
        let value = snakeLadder[keys[i]];
        let tdClass = 'normal';
        var snake = $("#snakeImage").html();
        var ladder=$("#ladderImage").html();
        var  img = '';
        var lineClass='';
        if(key>value) {
            tdClass ='snake';
            img = snake;
            lineClass  ='snakeLine';
        }else {
            tdClass ='ladder';
            img = ladder;
            lineClass  ='ladderLine';

        }
        $("#td_"+key).addClass(tdClass);
       // let targetText = ' <span class ="small">T: '+value+'</span>'
      //  $("#td_"+key).append(targetText);
        $("#td_"+key).html('');
        $("#td_"+key).append(img);

        $("#newGameMessage").after('<div id="'+lineClass+'_'+key+'_'+value+'" class="'+lineClass+'"></div>');

    }
}
function  loadPlayers(playerList) {
    let len = playerList.length;
    let players ='';
    let  classActive="";
    let playerObj=null;
    let playerId  =0;
    let playerName = " ";
    let playerCurrentPosition =0;
    for(let k= 0; k<len; k++) {
//        if(k==0) {
//            classActive ="redActive";
//        }else {
//            classActive =" ";
//        }
        playerObj = playerList[k];
        playerId  =  playerObj.playerId;
        playerName = playerObj.name;
        playerCurrentPosition =playerObj.currentPosition;
        players= players+'<li class="allPlayer '+classActive+'"  id ="liPlayer_'+playerId+'">';
        players= players+'<a href="javascript:void(0);" >'+playerName+' : ';
        players= players+'<span id="currentPositionPlayer_'+playerId+'">';
        players= players+playerCurrentPosition+'</span></a></li>';
    }
    $("#players").html(players);
 }

function  updatePlayerPosition(updatedPlayerList,currentPlayerTurn, previousPositionOfCurrentPlayer, snakeOrLadderPointer) {
    let len = updatedPlayerList.length;
    let playerObj=null;
    let playerId  =0;
    let playerCurrentPosition =0;
    let currentPositionOfCurrentPlayer =0;
    $(".currentPosition").remove();
    const colorClassPlayer = ['red','yellow','green', 'blue'];
    for(let k= 0; k<len; k++) {
        playerObj = updatedPlayerList[k];
        playerId  =  playerObj.playerId;
        playerCurrentPosition =playerObj.currentPosition;


        if(playerId != currentPlayerTurn) {
            $("#currentPositionPlayer_"+playerId).html(playerCurrentPosition);
            if(playerCurrentPosition>0) {
              $("#td_"+playerCurrentPosition).append('<span id="spanP'+playerId+'" class="currentPosition '+colorClassPlayer[k] +'"> P '+playerId+'</span>');
            }
        }else{
            currentPositionOfCurrentPlayer = playerCurrentPosition;
            $("#currentPositionPlayer_"+playerId).html(previousPositionOfCurrentPlayer+" → "+playerCurrentPosition);
        }
    }
    console.log("currentPositionOfCurrentPlayer"+currentPositionOfCurrentPlayer);
   let key = 0;
   let value = 0;
   let lineClass ='';
   let lineHoverClass ='';

    if(!($.isEmptyObject(snakeOrLadderPointer))){
        for (var i = 0, keys = Object.keys(snakeOrLadderPointer), keyLen = keys.length; i < keyLen; i++) {
             key = keys[i];
             value = snakeOrLadderPointer[keys[i]];
            if(key>value) {
                lineId  ='snakeLine'+'_'+key+'_'+value;
                lineClass ='snakeLine';
                lineHoverClass ='snakeLineHover';

            }else {
                lineId  ='ladderLine'+'_'+key+'_'+value;
                lineClass ='ladderLine';
                lineHoverClass ='ladderLineHover';
            }
            break;
        }
        console.log("key"+key);
        console.log("value"+value);
    }

    let targetStepPositionOfCurrentPlayer =0;
    if(key==0) {
        targetStepPositionOfCurrentPlayer = currentPositionOfCurrentPlayer;
    }else {
        targetStepPositionOfCurrentPlayer = key;
    }

    console.log("targetStepPositionOfCurrentPlayer="+targetStepPositionOfCurrentPlayer);
    console.log("previousPositionOfCurrentPlayer="+previousPositionOfCurrentPlayer);
    //if(previousPositionOfCurrentPlayer>0) {
       // $("#td_"+previousPositionOfCurrentPlayer).append('<span id="spanP'+currentPlayerTurn+'" class="currentPosition '+colorClassPlayer[currentPlayerTurn-1] +'"> P '+currentPlayerTurn+'</span>');
    let counter=1;
    if(targetStepPositionOfCurrentPlayer>0) {
    var colorClassOfCurrentPlayer= colorClassPlayer[currentPlayerTurn-1];

        for(let j=previousPositionOfCurrentPlayer; j<=targetStepPositionOfCurrentPlayer; j++) {
            moveCurrentPlayerPosition(j,currentPlayerTurn, colorClassOfCurrentPlayer,counter );
            counter++;
        }
    }

        if(value>0) {

          setTimeout(function() {
               // $("#"+lineId).trigger("mouseover");
              //  $("#"+lineId).hide('slow').show('slow').hide('slow').show('slow');
                $("#"+lineId).addClass(lineHoverClass);
                },500 * counter
            );
          //  $("#"+lineId ).trigger("mouseout");
           // $("#td_"+value).append('<span id="spanP'+currentPlayerTurn+'" class="currentPosition '+colorClassPlayer[currentPlayerTurn-1] +'"> P '+currentPlayerTurn+'</span>');
           //$("#"+lineId).fadeOut(1000, function(){$(this).removeClass(lineHoverClass, 1000)}).fadeIn(1000);

setTimeout(function() {

                $("."+lineHoverClass).removeClass(lineHoverClass);
               },500 * (counter+1)
            );

     setTimeout(function() {

                    $("#spanP"+currentPlayerTurn).remove();
                     $("#td_"+value).append('<span id="spanP'+currentPlayerTurn+'" class="currentPosition '+colorClassPlayer[currentPlayerTurn-1] +'"> P '+currentPlayerTurn+'</span>');
                    },500 * (counter+1)
                 );




            console.log(lineId +" is there");

        }
   // }
 }
 function  moveCurrentPlayerPosition(j,currentPlayerTurn, colorClassOfCurrentPlayer,counter){
 setTimeout(function() {
              $("#spanP"+currentPlayerTurn).hide().remove();
              $("#td_"+j).append('<span id="spanP'+currentPlayerTurn+'" class="currentPosition '+colorClassOfCurrentPlayer +'" style="display:none;"> P '+currentPlayerTurn+'</span>');
              $("#spanP"+currentPlayerTurn).show();
                  },500*counter
             );
 }

 function enterFullScreen(element) {
   if(element.requestFullscreen) {
     element.requestFullscreen();
   }else if (element.mozRequestFullScreen) {
     element.mozRequestFullScreen();     // Firefox
   }else if (element.webkitRequestFullscreen) {
     element.webkitRequestFullscreen();  // Safari
   }else if(element.msRequestFullscreen) {
     element.msRequestFullscreen();      // IE/Edge
   }
 }
