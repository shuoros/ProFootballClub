<template>
  <div>
    <h2 id="page-heading" data-cy="MatchHeading">
      <span v-text="$t('proFootballClubApp.match.home.title')" id="match-heading">Matches</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.match.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'MatchCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-match"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.match.home.createLabel')"> Create a new Match </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && matches && matches.length === 0">
      <span v-text="$t('proFootballClubApp.match.home.notFound')">No matches found</span>
    </div>
    <div class="table-responsive" v-if="matches && matches.length > 0">
      <table class="table table-striped" aria-describedby="matches">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.date')">Date</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.weather')">Weather</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.hostGoals')">Host Goals</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.guestGoals')">Guest Goals</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.events')">Events</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.type')">Type</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.host')">Host</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.guest')">Guest</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.hostComposition')">Host Composition</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.guestComposition')">Guest Composition</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.bestPlayer')">Best Player</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.league')">League</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.match.cupBoard')">Cup Board</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="match in matches" :key="match.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'MatchView', params: { matchId: match.id } }">{{ match.id }}</router-link>
            </td>
            <td>{{ match.date ? $d(Date.parse(match.date), 'short') : '' }}</td>
            <td v-text="$t('proFootballClubApp.Weather.' + match.weather)">{{ match.weather }}</td>
            <td>{{ match.hostGoals }}</td>
            <td>{{ match.guestGoals }}</td>
            <td>{{ match.events }}</td>
            <td v-text="$t('proFootballClubApp.MatchType.' + match.type)">{{ match.type }}</td>
            <td>
              <div v-if="match.host">
                <router-link :to="{ name: 'TeamView', params: { teamId: match.host.id } }">{{ match.host.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.guest">
                <router-link :to="{ name: 'TeamView', params: { teamId: match.guest.id } }">{{ match.guest.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.hostComposition">
                <router-link :to="{ name: 'CompositionView', params: { compositionId: match.hostComposition.id } }">{{
                  match.hostComposition.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.guestComposition">
                <router-link :to="{ name: 'CompositionView', params: { compositionId: match.guestComposition.id } }">{{
                  match.guestComposition.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.bestPlayer">
                <router-link :to="{ name: 'PlayerView', params: { playerId: match.bestPlayer.id } }">{{ match.bestPlayer.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.league">
                <router-link :to="{ name: 'LeagueView', params: { leagueId: match.league.id } }">{{ match.league.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="match.cupBoard">
                <router-link :to="{ name: 'CupBoardView', params: { cupBoardId: match.cupBoard.id } }">{{ match.cupBoard.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'MatchView', params: { matchId: match.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'MatchEdit', params: { matchId: match.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(match)"
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
        ><span id="proFootballClubApp.match.delete.question" data-cy="matchDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-match-heading" v-text="$t('proFootballClubApp.match.delete.question', { id: removeId })">
          Are you sure you want to delete this Match?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-match"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeMatch()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./match.component.ts"></script>
