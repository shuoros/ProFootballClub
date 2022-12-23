<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.league.home.createOrEditLabel"
          data-cy="LeagueCreateUpdateHeading"
          v-text="$t('proFootballClubApp.league.home.createOrEditLabel')"
        >
          Create or edit a League
        </h2>
        <div>
          <div class="form-group" v-if="league.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="league.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.league.clazz')" for="league-clazz">Clazz</label>
            <input
              type="number"
              class="form-control"
              name="clazz"
              id="league-clazz"
              data-cy="clazz"
              :class="{ valid: !$v.league.clazz.$invalid, invalid: $v.league.clazz.$invalid }"
              v-model.number="$v.league.clazz.$model"
              required
            />
            <div v-if="$v.league.clazz.$anyDirty && $v.league.clazz.$invalid">
              <small class="form-text text-danger" v-if="!$v.league.clazz.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.league.clazz.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.league.clazz.max" v-text="$t('entity.validation.max', { max: 14 })">
                This field cannot be longer than 14 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.league.clazz.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.league.events')" for="league-events">Events</label>
            <input
              type="text"
              class="form-control"
              name="events"
              id="league-events"
              data-cy="events"
              :class="{ valid: !$v.league.events.$invalid, invalid: $v.league.events.$invalid }"
              v-model="$v.league.events.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.league.finished')" for="league-finished">Finished</label>
            <input
              type="checkbox"
              class="form-check"
              name="finished"
              id="league-finished"
              data-cy="finished"
              :class="{ valid: !$v.league.finished.$invalid, invalid: $v.league.finished.$invalid }"
              v-model="$v.league.finished.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.league.start')" for="league-start">Start</label>
            <div class="d-flex">
              <input
                id="league-start"
                data-cy="start"
                type="datetime-local"
                class="form-control"
                name="start"
                :class="{ valid: !$v.league.start.$invalid, invalid: $v.league.start.$invalid }"
                :value="convertDateTimeFromServer($v.league.start.$model)"
                @change="updateInstantField('start', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.league.goalScorer')" for="league-goalScorer"
              >Goal Scorer</label
            >
            <select class="form-control" id="league-goalScorer" data-cy="goalScorer" name="goalScorer" v-model="league.goalScorer">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="league.goalScorer && playerOption.id === league.goalScorer.id ? league.goalScorer : playerOption"
                v-for="playerOption in players"
                :key="playerOption.id"
              >
                {{ playerOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.league.newsPaper')" for="league-newsPaper">News Paper</label>
            <select class="form-control" id="league-newsPaper" data-cy="newsPaper" name="newsPaper" v-model="league.newsPaper">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="league.newsPaper && newsPaperOption.id === league.newsPaper.id ? league.newsPaper : newsPaperOption"
                v-for="newsPaperOption in newsPapers"
                :key="newsPaperOption.id"
              >
                {{ newsPaperOption.id }}
              </option>
            </select>
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
            :disabled="$v.league.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./league-update.component.ts"></script>
