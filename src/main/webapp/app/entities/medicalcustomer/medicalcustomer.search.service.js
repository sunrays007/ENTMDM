(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .factory('MedicalcustomerSearch', MedicalcustomerSearch);

    MedicalcustomerSearch.$inject = ['$resource'];

    function MedicalcustomerSearch($resource) {
        var resourceUrl =  'api/_search/medicalcustomers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
