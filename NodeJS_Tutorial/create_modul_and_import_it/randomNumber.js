function randomNumber(min, max) {
	return Math.random() * (max - min) + min;
}

exports.getRandomNumber = function(min, max){
	return randomNumber(min, max);
};