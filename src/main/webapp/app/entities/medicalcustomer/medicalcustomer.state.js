(function() {
    'use strict';

    angular
        .module('entmdmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medicalcustomer', {
            parent: 'entity',
            url: '/medicalcustomer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'entmdmApp.medicalcustomer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medicalcustomer/medicalcustomers.html',
                    controller: 'MedicalcustomerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicalcustomer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medicalcustomer-detail', {
            parent: 'medicalcustomer',
            url: '/medicalcustomer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'entmdmApp.medicalcustomer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medicalcustomer/medicalcustomer-detail.html',
                    controller: 'MedicalcustomerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medicalcustomer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Medicalcustomer', function($stateParams, Medicalcustomer) {
                    return Medicalcustomer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medicalcustomer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medicalcustomer-detail.edit', {
            parent: 'medicalcustomer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicalcustomer/medicalcustomer-dialog.html',
                    controller: 'MedicalcustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medicalcustomer', function(Medicalcustomer) {
                            return Medicalcustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medicalcustomer.new', {
            parent: 'medicalcustomer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicalcustomer/medicalcustomer-dialog.html',
                    controller: 'MedicalcustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                medcustomernr: null,
                                address: null,
                                city: null,
                                state: null,
                                zipcode: null,
                                country: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medicalcustomer', null, { reload: 'medicalcustomer' });
                }, function() {
                    $state.go('medicalcustomer');
                });
            }]
        })
        .state('medicalcustomer.edit', {
            parent: 'medicalcustomer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicalcustomer/medicalcustomer-dialog.html',
                    controller: 'MedicalcustomerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medicalcustomer', function(Medicalcustomer) {
                            return Medicalcustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medicalcustomer', null, { reload: 'medicalcustomer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medicalcustomer.delete', {
            parent: 'medicalcustomer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medicalcustomer/medicalcustomer-delete-dialog.html',
                    controller: 'MedicalcustomerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medicalcustomer', function(Medicalcustomer) {
                            return Medicalcustomer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medicalcustomer', null, { reload: 'medicalcustomer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
