(function() {
    'use strict';
    angular
        .module('entmdmApp')
        .factory('Medicalcustomer', Medicalcustomer);

    Medicalcustomer.$inject = ['$resource'];

    function Medicalcustomer ($resource) {
        var resourceUrl =  'api/medicalcustomers/:id';

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
