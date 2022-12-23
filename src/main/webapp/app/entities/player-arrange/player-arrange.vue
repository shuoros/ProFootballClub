<template>
  <div>
    <h2 id="page-heading" data-cy="PlayerArrangeHeading">
      <span v-text="$t('proFootballClubApp.playerArrange.home.title')" id="player-arrange-heading">Player Arranges</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.playerArrange.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PlayerArrangeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-player-arrange"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.playerArrange.home.createLabel')"> Create a new Player Arrange </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && playerArranges && playerArranges.length === 0">
      <span v-text="$t('proFootballClubApp.playerArrange.home.notFound')">No playerArranges found</span>
    </div>
    <div class="table-responsive" v-if="playerArranges && playerArranges.length > 0">
      <table class="table table-striped" aria-describedby="playerArranges">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.playerArrange.post')">Post</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.playerArrange.player')">Player</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.playerArrange.composition')">Composition</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="playerArrange in playerArranges" :key="playerArrange.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PlayerArrangeView', params: { playerArrangeId: playerArrange.id } }">{{
                playerArrange.id
              }}</router-link>
            </td>
            <td v-text="$t('proFootballClubApp.Post.' + playerArrange.post)">{{ playerArrange.post }}</td>
            <td>
              <div v-if="playerArrange.player">
                <router-link :to="{ name: 'PlayerView', params: { playerId: playerArrange.player.id } }">{{
                  playerArrange.player.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="playerArrange.composition">
                <router-link :to="{ name: 'CompositionView', params: { compositionId: playerArrange.composition.id } }">{{
                  playerArrange.composition.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PlayerArrangeView', params: { playerArrangeId: playerArrange.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PlayerArrangeEdit', params: { playerArrangeId: playerArrange.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(playerArrange)"
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
          id="proFootballClubApp.playerArrange.delete.question"
          data-cy="playerArrangeDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-playerArrange-heading" v-text="$t('proFootballClubApp.playerArrange.delete.question', { id: removeId })">
          Are you sure you want to delete this Player Arrange?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-playerArrange"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePlayerArrange()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./player-arrange.component.ts"></script>
