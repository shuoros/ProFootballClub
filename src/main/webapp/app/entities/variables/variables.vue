<template>
  <div>
    <h2 id="page-heading" data-cy="VariablesHeading">
      <span v-text="$t('proFootballClubApp.variables.home.title')" id="variables-heading">Variables</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.variables.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'VariablesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-variables"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.variables.home.createLabel')"> Create a new Variables </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && variables && variables.length === 0">
      <span v-text="$t('proFootballClubApp.variables.home.notFound')">No variables found</span>
    </div>
    <div class="table-responsive" v-if="variables && variables.length > 0">
      <table class="table table-striped" aria-describedby="variables">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.variables.leagueFirstPlacePrize')">League First Place Prize</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.variables.leagueSecondPlacePrize')">League Second Place Prize</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="variables in variables" :key="variables.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VariablesView', params: { variablesId: variables.id } }">{{ variables.id }}</router-link>
            </td>
            <td>{{ variables.leagueFirstPlacePrize }}</td>
            <td>{{ variables.leagueSecondPlacePrize }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VariablesView', params: { variablesId: variables.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VariablesEdit', params: { variablesId: variables.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(variables)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="proFootballClubApp.variables.delete.question" data-cy="variablesDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-variables-heading" v-text="$t('proFootballClubApp.variables.delete.question', { id: removeId })">
          Are you sure you want to delete this Variables?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-variables"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeVariables()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./variables.component.ts"></script>
