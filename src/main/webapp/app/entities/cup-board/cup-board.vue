<template>
  <div>
    <h2 id="page-heading" data-cy="CupBoardHeading">
      <span v-text="$t('proFootballClubApp.cupBoard.home.title')" id="cup-board-heading">Cup Boards</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.cupBoard.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CupBoardCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-cup-board"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.cupBoard.home.createLabel')"> Create a new Cup Board </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && cupBoards && cupBoards.length === 0">
      <span v-text="$t('proFootballClubApp.cupBoard.home.notFound')">No cupBoards found</span>
    </div>
    <div class="table-responsive" v-if="cupBoards && cupBoards.length > 0">
      <table class="table table-striped" aria-describedby="cupBoards">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cupBoard.level')">Level</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.cupBoard.cup')">Cup</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cupBoard in cupBoards" :key="cupBoard.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CupBoardView', params: { cupBoardId: cupBoard.id } }">{{ cupBoard.id }}</router-link>
            </td>
            <td>{{ cupBoard.level }}</td>
            <td>
              <div v-if="cupBoard.cup">
                <router-link :to="{ name: 'CupView', params: { cupId: cupBoard.cup.id } }">{{ cupBoard.cup.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CupBoardView', params: { cupBoardId: cupBoard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CupBoardEdit', params: { cupBoardId: cupBoard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(cupBoard)"
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
        ><span id="proFootballClubApp.cupBoard.delete.question" data-cy="cupBoardDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-cupBoard-heading" v-text="$t('proFootballClubApp.cupBoard.delete.question', { id: removeId })">
          Are you sure you want to delete this Cup Board?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-cupBoard"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCupBoard()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./cup-board.component.ts"></script>
