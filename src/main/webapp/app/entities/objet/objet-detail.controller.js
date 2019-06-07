(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ObjetDetailController', ObjetDetailController);

    ObjetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Objet', 'Emplacement', 'ObjetCaracteristiques', 'User'];

    function ObjetDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Objet, Emplacement, ObjetCaracteristiques, User) {
        var vm = this;

        vm.objet = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('icebergApp:objetUpdate', function(event, result) {
            vm.objet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
