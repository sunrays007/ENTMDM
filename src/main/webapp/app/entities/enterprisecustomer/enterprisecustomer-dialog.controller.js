(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .controller('EnterprisecustomerDialogController', EnterprisecustomerDialogController);

    EnterprisecustomerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Enterprisecustomer'];

    function EnterprisecustomerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Enterprisecustomer) {
        var vm = this;

        vm.enterprisecustomer = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.enterprisecustomer.id !== null) {
                Enterprisecustomer.update(vm.enterprisecustomer, onSaveSuccess, onSaveError);
            } else {
                Enterprisecustomer.save(vm.enterprisecustomer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('entmdmApp:enterprisecustomerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
