<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.newsPaper.home.createOrEditLabel"
          data-cy="NewsPaperCreateUpdateHeading"
          v-text="$t('proFootballClubApp.newsPaper.home.createOrEditLabel')"
        >
          Create or edit a NewsPaper
        </h2>
        <div>
          <div class="form-group" v-if="newsPaper.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="newsPaper.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.newsPaper.news')" for="news-paper-news">News</label>
            <input
              type="text"
              class="form-control"
              name="news"
              id="news-paper-news"
              data-cy="news"
              :class="{ valid: !$v.newsPaper.news.$invalid, invalid: $v.newsPaper.news.$invalid }"
              v-model="$v.newsPaper.news.$model"
              required
            />
            <div v-if="$v.newsPaper.news.$anyDirty && $v.newsPaper.news.$invalid">
              <small class="form-text text-danger" v-if="!$v.newsPaper.news.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.newsPaper.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./news-paper-update.component.ts"></script>
