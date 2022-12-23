<template>
  <div>
    <h2 id="page-heading" data-cy="TransferHeading">
      <span v-text="$t('proFootballClubApp.transfer.home.title')" id="transfer-heading">Transfers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.transfer.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'TransferCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-transfer"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.transfer.home.createLabel')"> Create a new Transfer </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && transfers && transfers.length === 0">
      <span v-text="$t('proFootballClubApp.transfer.home.notFound')">No transfers found</span>
    </div>
    <div class="table-responsive" v-if="transfers && transfers.length > 0">
      <table class="table table-striped" aria-describedby="transfers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.transfer.price')">Price</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.transfer.password')">Password</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.transfer.bought')">Bought</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.transfer.player')">Player</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transfer in transfers" :key="transfer.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'TransferView', params: { transferId: transfer.id } }">{{ transfer.id }}</router-link>
            </td>
            <td>{{ transfer.price }}</td>
            <td>{{ transfer.password }}</td>
            <td>{{ transfer.bought }}</td>
            <td>
              <div v-if="transfer.player">
                <router-link :to="{ name: 'PlayerView', params: { playerId: transfer.player.id } }">{{ transfer.player.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'TransferView', params: { transferId: transfer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'TransferEdit', params: { transferId: transfer.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(transfer)"
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
        ><span id="proFootballClubApp.transfer.delete.question" data-cy="transferDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-transfer-heading" v-text="$t('proFootballClubApp.transfer.delete.question', { id: removeId })">
          Are you sure you want to delete this Transfer?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-transfer"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeTransfer()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./transfer.component.ts"></script>
