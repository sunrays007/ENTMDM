(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('MedicalcustomerDeleteController',MedicalcustomerDeleteController);

    MedicalcustomerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medicalcustomer'];

    function MedicalcustomerDeleteController($uibModalInstance, entity, Medicalcustomer) {
        var vm = this;

        vm.medicalcustomer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medicalcustomer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
