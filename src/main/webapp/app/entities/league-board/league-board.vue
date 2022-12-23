<template>
  <div>
    <h2 id="page-heading" data-cy="LeagueBoardHeading">
      <span v-text="$t('proFootballClubApp.leagueBoard.home.title')" id="league-board-heading">League Boards</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.leagueBoard.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'LeagueBoardCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-league-board"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.leagueBoard.home.createLabel')"> Create a new League Board </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && leagueBoards && leagueBoards.length === 0">
      <span v-text="$t('proFootballClubApp.leagueBoard.home.notFound')">No leagueBoards found</span>
    </div>
    <div class="table-responsive" v-if="leagueBoards && leagueBoards.length > 0">
      <table class="table table-striped" aria-describedby="leagueBoards">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.leagueBoard.position')">Position</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.leagueBoard.win')">Win</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.leagueBoard.lose')">Lose</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.leagueBoard.draw')">Draw</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.leagueBoard.goalDifference')">Goal Difference</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.leagueBoard.points')">Points</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.leagueBoard.team')">Team</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.leagueBoard.league')">League</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="leagueBoard in leagueBoards" :key="leagueBoard.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'LeagueBoardView', params: { leagueBoardId: leagueBoard.id } }">{{ leagueBoard.id }}</router-link>
            </td>
            <td>{{ leagueBoard.position }}</td>
            <td>{{ leagueBoard.win }}</td>
            <td>{{ leagueBoard.lose }}</td>
            <td>{{ leagueBoard.draw }}</td>
            <td>{{ leagueBoard.goalDifference }}</td>
            <td>{{ leagueBoard.points }}</td>
            <td>
              <div v-if="leagueBoard.team">
                <router-link :to="{ name: 'TeamView', params: { teamId: leagueBoard.team.id } }">{{ leagueBoard.team.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="leagueBoard.league">
                <router-link :to="{ name: 'LeagueView', params: { leagueId: leagueBoard.league.id } }">{{
                  leagueBoard.league.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'LeagueBoardView', params: { leagueBoardId: leagueBoard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'LeagueBoardEdit', params: { leagueBoardId: leagueBoard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(leagueBoard)"
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
          id="proFootballClubApp.leagueBoard.delete.question"
          data-cy="leagueBoardDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-leagueBoard-heading" v-text="$t('proFootballClubApp.leagueBoard.delete.question', { id: removeId })">
          Are you sure you want to delete this League Board?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-leagueBoard"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeLeagueBoard()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./league-board.component.ts"></script>
