<template>
  <div>
    <h2 id="page-heading" data-cy="PublicChatHeading">
      <span v-text="$t('proFootballClubApp.publicChat.home.title')" id="public-chat-heading">Public Chats</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.publicChat.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PublicChatCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-public-chat"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.publicChat.home.createLabel')"> Create a new Public Chat </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && publicChats && publicChats.length === 0">
      <span v-text="$t('proFootballClubApp.publicChat.home.notFound')">No publicChats found</span>
    </div>
    <div class="table-responsive" v-if="publicChats && publicChats.length > 0">
      <table class="table table-striped" aria-describedby="publicChats">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.publicChat.from')">From</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.publicChat.tu')">Tu</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="publicChat in publicChats" :key="publicChat.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PublicChatView', params: { publicChatId: publicChat.id } }">{{ publicChat.id }}</router-link>
            </td>
            <td>
              <div v-if="publicChat.from">
                <router-link :to="{ name: 'TeamView', params: { teamId: publicChat.from.id } }">{{ publicChat.from.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="publicChat.tu">
                <router-link :to="{ name: 'TeamView', params: { teamId: publicChat.tu.id } }">{{ publicChat.tu.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PublicChatView', params: { publicChatId: publicChat.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PublicChatEdit', params: { publicChatId: publicChat.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(publicChat)"
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
        ><span id="proFootballClubApp.publicChat.delete.question" data-cy="publicChatDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-publicChat-heading" v-text="$t('proFootballClubApp.publicChat.delete.question', { id: removeId })">
          Are you sure you want to delete this Public Chat?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-publicChat"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePublicChat()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./public-chat.component.ts"></script>
