(function() {
    'use strict';
    angular
        .module('icebergApp')
        .factory('ParamEmpl', ParamEmpl);

    ParamEmpl.$inject = ['$resource'];

    function ParamEmpl ($resource) {
        var resourceUrl =  'api/param-empls/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
