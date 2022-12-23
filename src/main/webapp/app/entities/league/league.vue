<template>
  <div>
    <h2 id="page-heading" data-cy="LeagueHeading">
      <span v-text="$t('proFootballClubApp.league.home.title')" id="league-heading">Leagues</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.league.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LeagueCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-league"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.league.home.createLabel')"> Create a new League </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && leagues && leagues.length === 0">
      <span v-text="$t('proFootballClubApp.league.home.notFound')">No leagues found</span>
    </div>
    <div class="table-responsive" v-if="leagues && leagues.length > 0">
      <table class="table table-striped" aria-describedby="leagues">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.league.clazz')">Clazz</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.league.events')">Events</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.league.finished')">Finished</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.league.start')">Start</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.league.goalScorer')">Goal Scorer</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.league.newsPaper')">News Paper</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="league in leagues" :key="league.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LeagueView', params: { leagueId: league.id } }">{{ league.id }}</router-link>
            </td>
            <td>{{ league.clazz }}</td>
            <td>{{ league.events }}</td>
            <td>{{ league.finished }}</td>
            <td>{{ league.start ? $d(Date.parse(league.start), 'short') : '' }}</td>
            <td>
              <div v-if="league.goalScorer">
                <router-link :to="{ name: 'PlayerView', params: { playerId: league.goalScorer.id } }">{{
                  league.goalScorer.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="league.newsPaper">
                <router-link :to="{ name: 'NewsPaperView', params: { newsPaperId: league.newsPaper.id } }">{{
                  league.newsPaper.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LeagueView', params: { leagueId: league.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LeagueEdit', params: { leagueId: league.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(league)"
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
        ><span id="proFootballClubApp.league.delete.question" data-cy="leagueDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-league-heading" v-text="$t('proFootballClubApp.league.delete.question', { id: removeId })">
          Are you sure you want to delete this League?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-league"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLeague()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./league.component.ts"></script>
