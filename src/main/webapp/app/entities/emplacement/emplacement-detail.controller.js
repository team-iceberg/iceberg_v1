(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('EmplacementDetailController', EmplacementDetailController);

    EmplacementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Emplacement', 'Objet', 'DetailEmplacement', 'ParamEmpl'];

    function EmplacementDetailController($scope, $rootScope, $stateParams, previousState, entity, Emplacement, Objet, DetailEmplacement, ParamEmpl) {
        var vm = this;

        vm.emplacement = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('icebergApp:emplacementUpdate', function(event, result) {
            vm.emplacement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
