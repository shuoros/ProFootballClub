<template>
  <div>
    <h2 id="page-heading" data-cy="PrivateChatHeading">
      <span v-text="$t('proFootballClubApp.privateChat.home.title')" id="private-chat-heading">Private Chats</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.privateChat.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PrivateChatCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-private-chat"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.privateChat.home.createLabel')"> Create a new Private Chat </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && privateChats && privateChats.length === 0">
      <span v-text="$t('proFootballClubApp.privateChat.home.notFound')">No privateChats found</span>
    </div>
    <div class="table-responsive" v-if="privateChats && privateChats.length > 0">
      <table class="table table-striped" aria-describedby="privateChats">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.privateChat.league')">League</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.privateChat.cup')">Cup</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="privateChat in privateChats" :key="privateChat.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PrivateChatView', params: { privateChatId: privateChat.id } }">{{ privateChat.id }}</router-link>
            </td>
            <td>
              <div v-if="privateChat.league">
                <router-link :to="{ name: 'LeagueView', params: { leagueId: privateChat.league.id } }">{{
                  privateChat.league.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="privateChat.cup">
                <router-link :to="{ name: 'CupView', params: { cupId: privateChat.cup.id } }">{{ privateChat.cup.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PrivateChatView', params: { privateChatId: privateChat.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PrivateChatEdit', params: { privateChatId: privateChat.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(privateChat)"
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
          id="proFootballClubApp.privateChat.delete.question"
          data-cy="privateChatDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-privateChat-heading" v-text="$t('proFootballClubApp.privateChat.delete.question', { id: removeId })">
          Are you sure you want to delete this Private Chat?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-privateChat"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePrivateChat()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./private-chat.component.ts"></script>
