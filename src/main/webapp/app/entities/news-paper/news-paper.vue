<template>
  <div>
    <h2 id="page-heading" data-cy="NewsPaperHeading">
      <span v-text="$t('proFootballClubApp.newsPaper.home.title')" id="news-paper-heading">News Papers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proFootballClubApp.newsPaper.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'NewsPaperCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-news-paper"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proFootballClubApp.newsPaper.home.createLabel')"> Create a new News Paper </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && newsPapers && newsPapers.length === 0">
      <span v-text="$t('proFootballClubApp.newsPaper.home.notFound')">No newsPapers found</span>
    </div>
    <div class="table-responsive" v-if="newsPapers && newsPapers.length > 0">
      <table class="table table-striped" aria-describedby="newsPapers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('proFootballClubApp.newsPaper.news')">News</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="newsPaper in newsPapers" :key="newsPaper.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'NewsPaperView', params: { newsPaperId: newsPaper.id } }">{{ newsPaper.id }}</router-link>
            </td>
            <td>{{ newsPaper.news }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'NewsPaperView', params: { newsPaperId: newsPaper.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'NewsPaperEdit', params: { newsPaperId: newsPaper.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(newsPaper)"
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
        ><span id="proFootballClubApp.newsPaper.delete.question" data-cy="newsPaperDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-newsPaper-heading" v-text="$t('proFootballClubApp.newsPaper.delete.question', { id: removeId })">
          Are you sure you want to delete this News Paper?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-newsPaper"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeNewsPaper()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./news-paper.component.ts"></script>
