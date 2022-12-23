<template>
  <div>
    <h2 id="page-heading" data-cy="CompositionHeading">
      <span v-text="$t('proFootballClubApp.composition.home.title')" id="composition-heading">Compositions</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.composition.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CompositionCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-composition"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.composition.home.createLabel')"> Create a new Composition </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && compositions && compositions.length === 0">
      <span v-text="$t('proFootballClubApp.composition.home.notFound')">No compositions found</span>
    </div>
    <div class="table-responsive" v-if="compositions && compositions.length > 0">
      <table class="table table-striped" aria-describedby="compositions">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.composition.d3fault')">D 3 Fault</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.composition.arrange')">Arrange</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.composition.strategy')">Strategy</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.composition.defence')">Defence</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.composition.shortPass')">Short Pass</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.composition.violence')">Violence</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.composition.capitan')">Capitan</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.composition.team')">Team</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="composition in compositions" :key="composition.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CompositionView', params: { compositionId: composition.id } }">{{ composition.id }}</router-link>
            </td>
            <td>{{ composition.d3fault }}</td>
            <td v-text="$t('proFootballClubApp.Arrange.' + composition.arrange)">{{ composition.arrange }}</td>
            <td v-text="$t('proFootballClubApp.Strategy.' + composition.strategy)">{{ composition.strategy }}</td>
            <td>{{ composition.defence }}</td>
            <td>{{ composition.shortPass }}</td>
            <td>{{ composition.violence }}</td>
            <td>
              <div v-if="composition.capitan">
                <router-link :to="{ name: 'PlayerView', params: { playerId: composition.capitan.id } }">{{
                  composition.capitan.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="composition.team">
                <router-link :to="{ name: 'TeamView', params: { teamId: composition.team.id } }">{{ composition.team.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CompositionView', params: { compositionId: composition.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CompositionEdit', params: { compositionId: composition.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(composition)"
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
        ><span
          id="proFootballClubApp.composition.delete.question"
          data-cy="compositionDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-composition-heading" v-text="$t('proFootballClubApp.composition.delete.question', { id: removeId })">
          Are you sure you want to delete this Composition?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-composition"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeComposition()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./composition.component.ts"></script>
