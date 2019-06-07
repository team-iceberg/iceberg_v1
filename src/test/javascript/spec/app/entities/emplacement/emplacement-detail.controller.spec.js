'use strict';

describe('Controller Tests', function() {

    describe('Emplacement Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmplacement, MockObjet, MockDetailEmplacement, MockParamEmpl;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmplacement = jasmine.createSpy('MockEmplacement');
            MockObjet = jasmine.createSpy('MockObjet');
            MockDetailEmplacement = jasmine.createSpy('MockDetailEmplacement');
            MockParamEmpl = jasmine.createSpy('MockParamEmpl');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Emplacement': MockEmplacement,
                'Objet': MockObjet,
                'DetailEmplacement': MockDetailEmplacement,
                'ParamEmpl': MockParamEmpl
            };
            createController = function() {
                $injector.get('$controller')("EmplacementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'icebergApp:emplacementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
