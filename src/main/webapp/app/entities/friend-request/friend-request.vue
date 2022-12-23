<template>
  <div>
    <h2 id="page-heading" data-cy="FriendRequestHeading">
      <span v-text="$t('proFootballClubApp.friendRequest.home.title')" id="friend-request-heading">Friend Requests</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.friendRequest.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FriendRequestCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-friend-request"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.friendRequest.home.createLabel')"> Create a new Friend Request </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && friendRequests && friendRequests.length === 0">
      <span v-text="$t('proFootballClubApp.friendRequest.home.notFound')">No friendRequests found</span>
    </div>
    <div class="table-responsive" v-if="friendRequests && friendRequests.length > 0">
      <table class="table table-striped" aria-describedby="friendRequests">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.friendRequest.from')">From</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.friendRequest.tu')">Tu</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="friendRequest in friendRequests" :key="friendRequest.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FriendRequestView', params: { friendRequestId: friendRequest.id } }">{{
                friendRequest.id
              }}</router-link>
            </td>
            <td>
              <div v-if="friendRequest.from">
                <router-link :to="{ name: 'TeamView', params: { teamId: friendRequest.from.id } }">{{ friendRequest.from.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="friendRequest.tu">
                <router-link :to="{ name: 'TeamView', params: { teamId: friendRequest.tu.id } }">{{ friendRequest.tu.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'FriendRequestView', params: { friendRequestId: friendRequest.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'FriendRequestEdit', params: { friendRequestId: friendRequest.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(friendRequest)"
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
          id="proFootballClubApp.friendRequest.delete.question"
          data-cy="friendRequestDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-friendRequest-heading" v-text="$t('proFootballClubApp.friendRequest.delete.question', { id: removeId })">
          Are you sure you want to delete this Friend Request?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-friendRequest"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFriendRequest()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./friend-request.component.ts"></script>
