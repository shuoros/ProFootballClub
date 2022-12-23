<template>
  <div>
    <h2 id="page-heading" data-cy="TrainHeading">
      <span v-text="$t('proFootballClubApp.train.home.title')" id="train-heading">Trains</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.train.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TrainCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-train"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.train.home.createLabel')"> Create a new Train </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && trains && trains.length === 0">
      <span v-text="$t('proFootballClubApp.train.home.notFound')">No trains found</span>
    </div>
    <div class="table-responsive" v-if="trains && trains.length > 0">
      <table class="table table-striped" aria-describedby="trains">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.train.training')">Training</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.train.finishes')">Finishes</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.train.player')">Player</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="train in trains" :key="train.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TrainView', params: { trainId: train.id } }">{{ train.id }}</router-link>
            </td>
            <td v-text="$t('proFootballClubApp.Training.' + train.training)">{{ train.training }}</td>
            <td>{{ train.finishes ? $d(Date.parse(train.finishes), 'short') : '' }}</td>
            <td>
              <div v-if="train.player">
                <router-link :to="{ name: 'PlayerView', params: { playerId: train.player.id } }">{{ train.player.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TrainView', params: { trainId: train.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TrainEdit', params: { trainId: train.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(train)"
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
        ><span id="proFootballClubApp.train.delete.question" data-cy="trainDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-train-heading" v-text="$t('proFootballClubApp.train.delete.question', { id: removeId })">
          Are you sure you want to delete this Train?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-train"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTrain()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./train.component.ts"></script>
