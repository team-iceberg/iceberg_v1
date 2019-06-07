'use strict';

describe('Controller Tests', function() {

    describe('DetailEmplacement Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDetailEmplacement, MockEmplacement, MockReservation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDetailEmplacement = jasmine.createSpy('MockDetailEmplacement');
            MockEmplacement = jasmine.createSpy('MockEmplacement');
            MockReservation = jasmine.createSpy('MockReservation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'DetailEmplacement': MockDetailEmplacement,
                'Emplacement': MockEmplacement,
                'Reservation': MockReservation
            };
            createController = function() {
                $injector.get('$controller')("DetailEmplacementDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'icebergApp:detailEmplacementUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
