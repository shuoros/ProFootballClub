<template>
  <div>
    <h2 id="page-heading" data-cy="CupHeading">
      <span v-text="$t('proFootballClubApp.cup.home.title')" id="cup-heading">Cups</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.cup.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CupCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-cup">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.cup.home.createLabel')"> Create a new Cup </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cups && cups.length === 0">
      <span v-text="$t('proFootballClubApp.cup.home.notFound')">No cups found</span>
    </div>
    <div class="table-responsive" v-if="cups && cups.length > 0">
      <table class="table table-striped" aria-describedby="cups">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cup.type')">Type</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cup.events')">Events</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cup.finished')">Finished</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cup.entrance')">Entrance</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cup.start')">Start</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cup.goalScorer')">Goal Scorer</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cup.newsPaper')">News Paper</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cup in cups" :key="cup.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CupView', params: { cupId: cup.id } }">{{ cup.id }}</router-link>
            </td>
            <td v-text="$t('proFootballClubApp.CupType.' + cup.type)">{{ cup.type }}</td>
            <td>{{ cup.events }}</td>
            <td>{{ cup.finished }}</td>
            <td>{{ cup.entrance }}</td>
            <td>{{ cup.start ? $d(Date.parse(cup.start), 'short') : '' }}</td>
            <td>
              <div v-if="cup.goalScorer">
                <router-link :to="{ name: 'PlayerView', params: { playerId: cup.goalScorer.id } }">{{ cup.goalScorer.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="cup.newsPaper">
                <router-link :to="{ name: 'NewsPaperView', params: { newsPaperId: cup.newsPaper.id } }">{{ cup.newsPaper.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CupView', params: { cupId: cup.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CupEdit', params: { cupId: cup.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cup)"
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
        ><span id="proFootballClubApp.cup.delete.question" data-cy="cupDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-cup-heading" v-text="$t('proFootballClubApp.cup.delete.question', { id: removeId })">
          Are you sure you want to delete this Cup?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-cup"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCup()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./cup.component.ts"></script>
