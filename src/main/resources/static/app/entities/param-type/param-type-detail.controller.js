(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamTypeDetailController', ParamTypeDetailController);

    ParamTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ParamType'];

    function ParamTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, ParamType) {
        var vm = this;

        vm.paramType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('icebergApp:paramTypeUpdate', function(event, result) {
            vm.paramType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
