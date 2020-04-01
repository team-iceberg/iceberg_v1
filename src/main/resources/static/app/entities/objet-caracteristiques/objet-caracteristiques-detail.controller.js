(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ObjetCaracteristiquesDetailController', ObjetCaracteristiquesDetailController);

    ObjetCaracteristiquesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ObjetCaracteristiques', 'Objet'];

    function ObjetCaracteristiquesDetailController($scope, $rootScope, $stateParams, previousState, entity, ObjetCaracteristiques, Objet) {
        var vm = this;

        vm.objetCaracteristiques = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('icebergApp:objetCaracteristiquesUpdate', function(event, result) {
            vm.objetCaracteristiques = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
