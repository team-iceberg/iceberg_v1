(function() {
    'use strict';

    angular
        .module('icebergApp')
        .controller('ParamTailleDetailController', ParamTailleDetailController);

    ParamTailleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ParamTaille'];

    function ParamTailleDetailController($scope, $rootScope, $stateParams, previousState, entity, ParamTaille) {
        var vm = this;

        vm.paramTaille = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('icebergApp:paramTailleUpdate', function(event, result) {
            vm.paramTaille = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
