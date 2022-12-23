<template>
  <div>
    <h2 id="page-heading" data-cy="TeamHeading">
      <span v-text="$t('proFootballClubApp.team.home.title')" id="team-heading">Teams</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.team.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TeamCreate' }" custom v-slot="{ navigate }">
          <button @click="navigate" id="jh-create-entity" data-cy="entityCreateButton" class="btn btn-primary jh-create-entity create-team">
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.team.home.createLabel')"> Create a new Team </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && teams && teams.length === 0">
      <span v-text="$t('proFootballClubApp.team.home.notFound')">No teams found</span>
    </div>
    <div class="table-responsive" v-if="teams && teams.length > 0">
      <table class="table table-striped" aria-describedby="teams">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.name')">Name</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.homeland')">Homeland</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.gender')">Gender</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.money')">Money</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.points')">Points</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.matches')">Matches</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.trophies')">Trophies</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.readiness')">Readiness</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.spirit')">Spirit</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.fans')">Fans</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.fansSatisfaction')">Fans Satisfaction</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.stadium')">Stadium</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.coach')">Coach</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.team.team')">Team</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="team in teams" :key="team.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TeamView', params: { teamId: team.id } }">{{ team.id }}</router-link>
            </td>
            <td>{{ team.name }}</td>
            <td v-text="$t('proFootballClubApp.Country.' + team.homeland)">{{ team.homeland }}</td>
            <td v-text="$t('proFootballClubApp.Gender.' + team.gender)">{{ team.gender }}</td>
            <td>{{ team.money }}</td>
            <td>{{ team.points }}</td>
            <td>{{ team.matches }}</td>
            <td>{{ team.trophies }}</td>
            <td>{{ team.readiness }}</td>
            <td>{{ team.spirit }}</td>
            <td>{{ team.fans }}</td>
            <td>{{ team.fansSatisfaction }}</td>
            <td>
              <div v-if="team.stadium">
                <router-link :to="{ name: 'StadiumView', params: { stadiumId: team.stadium.id } }">{{ team.stadium.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="team.coach">
                <router-link :to="{ name: 'CoachView', params: { coachId: team.coach.id } }">{{ team.coach.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="team.team">
                <router-link :to="{ name: 'TeamView', params: { teamId: team.team.id } }">{{ team.team.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TeamView', params: { teamId: team.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TeamEdit', params: { teamId: team.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(team)"
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
        ><span id="proFootballClubApp.team.delete.question" data-cy="teamDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-team-heading" v-text="$t('proFootballClubApp.team.delete.question', { id: removeId })">
          Are you sure you want to delete this Team?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-team"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTeam()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./team.component.ts"></script>
