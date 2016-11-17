/**
 * Controller responsável pela manipulação do fragmento de página
 * list-questions.html
 * 
 * @created
 * @date 21-10-16.
 */
aqtApp.controller("responseQuestionController", function($scope, userService, questionService, $location, localStorageService, growl) {
	
	var vm = this;
	
	vm.chosenQuestions = [];
	vm.optionsQuestionsClicked = questionService.getOptionsQuestionsClicked();
	vm.checkedQuestionsClicked = [];
	vm.question = {};
	vm.other = false;
	vm.otherText = '';
	
	var userStorage = localStorageService.get("aqt-user");
	var elemetQuestion = $("#body-detail-description");
	
	vm.toggleCheck = function (option) {
        if (vm.checkedQuestionsClicked.indexOf(option) === -1) {
        	vm.checkedQuestionsClicked.push(option);
        } else {
        	vm.checkedQuestionsClicked.splice(vm.checkedQuestionsClicked.indexOf(option), 1);
        }
    };
	userService.getById(userStorage.id).$promise.then(
			function(data) {
				vm.chosenQuestions = data.chosenQuestionsWrapper.chosenQuestions;
				vm.question = vm.chosenQuestions[0];
				elemetQuestion.append(vm.question.descritptionHtml);
			}, function(data) {
	});
	
	vm.nextQuestion = function(){
		if(vm.other){
			vm.checkedQuestionsClicked.push(vm.otherText);
		}
		console.log(vm.frmOptions)
		if(vm.checkedQuestionsClicked.length && vm.frmOptions.$valid){
			next();
		}else{
			growl.error("Escolha no mínimo um motivo");
		}
	}

	var cont = 0;
	var next = function() {
		cont++;
		elemetQuestion.empty();
		vm.question = vm.chosenQuestions[cont];
		elemetQuestion.append(vm.question.descritptionHtml);
		reset();
	};
	
	vm.disableBtnNext = function(){
		return vm.chosenQuestions.length-1 === cont;
	};
	
	
    var reset = function() {
    	vm.checkedQuestionsClicked = angular.copy(vm.checkedQuestionsClicked = []);
    	vm.other = false;
    	vm.otherText = '';
      };
});