
function redirectToPetition(e){
    let tgt = e.currentTarget;
    let redirectUrl = tgt.getAttribute('data-href');
    console.log(tgt);
    console.log(redirectUrl);
    window.location = redirectUrl;
 }




function addClickToViewCards(){
    let viewCards = document.getElementsByClassName("clickable")
    for (let v = 0; v < viewCards.length; v++){
        viewCards[v].addEventListener("click", redirectToPetition, false);
    }

}



function init(){
    addClickToViewCards();
}

window.onload = init