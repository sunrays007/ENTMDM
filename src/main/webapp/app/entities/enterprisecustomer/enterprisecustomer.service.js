(function() {
    'use strict';
    angular
        .module('entmdmApp')
        .factory('Enterprisecustomer', Enterprisecustomer);

    Enterprisecustomer.$inject = ['$resource'];

    function Enterprisecustomer ($resource) {
        var resourceUrl =  'api/enterprisecustomers/:id';

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
