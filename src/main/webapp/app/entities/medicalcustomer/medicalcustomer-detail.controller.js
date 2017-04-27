(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('MedicalcustomerDetailController', MedicalcustomerDetailController);

    MedicalcustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medicalcustomer'];

    function MedicalcustomerDetailController($scope, $rootScope, $stateParams, previousState, entity, Medicalcustomer) {
        var vm = this;

        vm.medicalcustomer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('entmdmApp:medicalcustomerUpdate', function(event, result) {
            vm.medicalcustomer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
