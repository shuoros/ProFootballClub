<template>
  <div>
    <h2 id="page-heading" data-cy="PlayerHeading">
      <span v-text="$t('proFootballClubApp.player.home.title')" id="player-heading">Players</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.player.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PlayerCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-player"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.player.home.createLabel')"> Create a new Player </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && players && players.length === 0">
      <span v-text="$t('proFootballClubApp.player.home.notFound')">No players found</span>
    </div>
    <div class="table-responsive" v-if="players && players.length > 0">
      <table class="table table-striped" aria-describedby="players">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.firstName')">First Name</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.lastName')">Last Name</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.gender')">Gender</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.country')">Country</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.age')">Age</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.status')">Status</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.post')">Post</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.totalPower')">Total Power</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.goalkeeping')">Goalkeeping</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.defence')">Defence</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.tackling')">Tackling</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.passing')">Passing</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.teamSkill')">Team Skill</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.ballSkill')">Ball Skill</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.shooting')">Shooting</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.longShooting')">Long Shooting</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.dribbling')">Dribbling</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.technique')">Technique</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.confidence')">Confidence</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.player.team')">Team</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="player in players" :key="player.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PlayerView', params: { playerId: player.id } }">{{ player.id }}</router-link>
            </td>
            <td>{{ player.firstName }}</td>
            <td>{{ player.lastName }}</td>
            <td v-text="$t('proFootballClubApp.Gender.' + player.gender)">{{ player.gender }}</td>
            <td v-text="$t('proFootballClubApp.Country.' + player.country)">{{ player.country }}</td>
            <td>{{ player.age }}</td>
            <td v-text="$t('proFootballClubApp.PlayerStatus.' + player.status)">{{ player.status }}</td>
            <td v-text="$t('proFootballClubApp.Post.' + player.post)">{{ player.post }}</td>
            <td>{{ player.totalPower }}</td>
            <td>{{ player.goalkeeping }}</td>
            <td>{{ player.defence }}</td>
            <td>{{ player.tackling }}</td>
            <td>{{ player.passing }}</td>
            <td>{{ player.teamSkill }}</td>
            <td>{{ player.ballSkill }}</td>
            <td>{{ player.shooting }}</td>
            <td>{{ player.longShooting }}</td>
            <td>{{ player.dribbling }}</td>
            <td>{{ player.technique }}</td>
            <td>{{ player.confidence }}</td>
            <td>
              <div v-if="player.team">
                <router-link :to="{ name: 'TeamView', params: { teamId: player.team.id } }">{{ player.team.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PlayerView', params: { playerId: player.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PlayerEdit', params: { playerId: player.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(player)"
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
        ><span id="proFootballClubApp.player.delete.question" data-cy="playerDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-player-heading" v-text="$t('proFootballClubApp.player.delete.question', { id: removeId })">
          Are you sure you want to delete this Player?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-player"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePlayer()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./player.component.ts"></script>
