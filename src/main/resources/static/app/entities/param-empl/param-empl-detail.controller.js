(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamEmplDetailController', ParamEmplDetailController);

    ParamEmplDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ParamEmpl'];

    function ParamEmplDetailController($scope, $rootScope, $stateParams, previousState, entity, ParamEmpl) {
        var vm = this;

        vm.paramEmpl = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('icebergApp:paramEmplUpdate', function(event, result) {
            vm.paramEmpl = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
