function Player(){
    //variables
    this.characterCard;
    this.character;
    this.characterInfo;
    this.JobCard;
    this.job;
    this.mission;
    this.life;
    this.mountedCardList; // list of name-image pair
    this.inHandCardList; // list of name-image pair
    //functions
    Player.prototype.showPlayerInfo = function(){
        /* show information to the user
         * add event listener to each card images*/
    }
}

function CardList(){
    this.cardList;

}

function OtherPlayers(){
    this.otherPlayersList;
    OtherPlayers.prototype.showOtherPlayersList = function(){
    }
}

function CardInfo(){
    //variables
    this.cardImage;
    this.cardInfo;
    //functions
    CardInfo.prototype.showCardInfo = function(){
    }
}

